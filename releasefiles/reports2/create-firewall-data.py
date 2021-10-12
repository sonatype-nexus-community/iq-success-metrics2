import json
import requests
import os
import os.path
import sys
import shutil
import csv

debug = False

iqurl = sys.argv[1]
iquser = sys.argv[2]
iqpwd = sys.argv[3]

if len(sys.argv) == 5:
    if sys.argv[4] == "debug":
        debug = True
        
jsonfile = 'firewalldata.json'
csvfile = 'firewalldata.csv'

firewall_api = "api/experimental/firewall"
quarantine_datadir = "./quarantine_data"

if os.path.exists(quarantine_datadir):
	shutil.rmtree(quarantine_datadir)

os.mkdir(quarantine_datadir)


def get_nexusiq_data(end_point):
	url = "{}/{}/{}" . format(iqurl, firewall_api, end_point)

	req = requests.get(url, auth=(iquser, iqpwd), verify=False)

	if req.status_code == 200:
		res = req.json()
	else:
		res = "Error fetching data"

	return res


def print_json(json_data, json_file):
    output_file = "{}/{}{}".format(quarantine_datadir, json_file, ".json")
    json_formatted = json.dumps(json_data, indent=2)
    print(json_formatted)
    
    with open(output_file, 'w') as outfile:
        json.dump(json_data, outfile, indent=2)
    
    return
    

def summary_report(report_name, end_point):
    data = get_nexusiq_data(end_point)
    print_json(data, report_name)
    
    csv_file = "{}/{}{}".format(quarantine_datadir, report_name, ".csv")
    with open(csv_file, 'w') as fd:
        writer = csv.writer(fd, delimiter=",")
        
        # print header
        line = []
        for key in data.keys():
            line.append(key)
        
        writer.writerow(line)

        # print data
        line = []
        for value in data.values():
            line.append(value)
        
        writer.writerow(line)

    return


def list_report(report_name, end_point):
    page = 1
    page_size = 100
    page_count = page_query(end_point, page, page_size, report_name)
    
    if page_count > 0:
        while page <= page_count:
            page += 1
            page_query(end_point, page, page_size, report_name)

    return


def page_query(end_point, page, page_size, report_name):
    asc = True

    if report_name == "autoreleased_from_quarantine_components":
        sort_by = "releaseQuarantineTime"
    else:
        sort_by = "quarantineTime"
        
    query = "{}?page={}&pageSize={}&sortBy={}&asc={}".format(end_point, page, page_size, sort_by, asc)
    data = get_nexusiq_data(query)
    
    page_count = data["pageCount"]
    results = data["results"]
    
    if len(results) > 0:
        print_list_report(data["results"], report_name, page)
    
    return page_count

    
def print_list_report(results, report_name, page):
    
    if debug:
        print_json(results, report_name + "_" + str(page))

    csv_file = "{}/{}{}".format(quarantine_datadir, report_name, ".csv")
    with open(csv_file, 'a') as fd:
        writer = csv.writer(fd, delimiter=",")
        
        line = []
        line.append("repository")
        line.append("quarantine_date")
        line.append("date_cleared")
        line.append("path_name")
        line.append("format")
        line.append("quarantined")
        line.append("policy_name")
        line.append("threat_level")
        line.append("cve")
        writer.writerow(line)
                
        for result in results:
            repository = result["repository"]
            quarantine_date = result["quarantineDate"]
            date_cleared = result["dateCleared"]
            path_name = result["pathname"]
            quarantined = result["quarantined"]
            format = result["componentIdentifier"]["format"]
            
            if result["quarantinePolicyViolations"]:
                for quarantinePolicyViolation in result["quarantinePolicyViolations"]:
                    policy_name = quarantinePolicyViolation["policyName"]
                    threat_level = quarantinePolicyViolation["threatLevel"]
                    
                    for constraint in quarantinePolicyViolation["constraintViolations"]:
                        cve = getCVE(constraint["reasons"])
                    
                        line = []
                        line.append(repository)
                        line.append(quarantine_date)
                        line.append(date_cleared)
                        line.append(path_name)
                        line.append(format)
                        line.append(quarantined)
                        line.append(policy_name)
                        line.append(threat_level)
                        line.append(cve)
                        writer.writerow(line)
            else:
                line = []
                line.append(repository)
                line.append(quarantine_date)
                line.append(date_cleared)
                line.append(path_name)
                line.append(format)
                line.append(quarantined)
                line.append()
                line.append()
                writer.writerow(line)

    return


def itemExists(item,items):
    exists = False
    
    for i in items:
        if i == item:
            exists = True
            break
        
    return exists
        
        
def getCVE(reasons):
    values = []
    f = ""
    
    for reason in reasons:
        reference = reason["reference"]
        
        if not reference is None:
            newValue = reference["value"]
            if not itemExists(newValue, values):
                values.append(newValue)
        
    for v in values:
        f = f.join(v + ":")
        
    f = f[:-1]
    
    return f


def autoreleased_from_quarantine_config():
    end_point = "releaseQuarantine/configuration"
    data = get_nexusiq_data(end_point)
    print_json(data, "autoreleased_from_quarantine_config")
    
    csv_file = "{}/{}{}".format(quarantine_datadir, "autoreleased_from_quarantine_config", ".csv")
    with open(csv_file, 'w') as fd:
        writer = csv.writer(fd, delimiter=",")
        
        # print header
        line = []
        line.append("id")
        line.append("name")
        line.append("autoReleaseQuarantineEnabled")

        writer.writerow(line)

        # print data
        for d in data:
            line = []
            line.append(d["id"])
            line.append(d["name"])
            line.append(d["autoReleaseQuarantineEnabled"])
            writer.writerow(line)
            
    return


def main():
    
    summary_report("autoreleased_from_quarantine_summary", "releaseQuarantine/summary")
    summary_report("quarantined_components_summary", "quarantine/summary")

    autoreleased_from_quarantine_config()
    
    list_report("autoreleased_from_quarantine_components", "components/autoReleasedFromQuarantine")
    list_report("quarantined_components", "components/quarantined")
    
	
if __name__ == '__main__':
	main()
