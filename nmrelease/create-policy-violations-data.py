import json
import requests
import os
import os.path
import sys


iqurl = sys.argv[1]
iquser = sys.argv[2]
iqpwd = sys.argv[3]

jsonfile = 'policyviolations.json'
csvfile = 'policyviolations.csv'

def getNexusIqData(api):
	url = "{}{}" . format(iqurl, api)

	req = requests.get(url, auth=(iquser, iqpwd), verify=False)

	if req.status_code == 200:
		res = req.json()
	else:
		res = "Error fetching data"

	return res

def getCVEValue(d):
	cve = "none"

	if type(d) is dict:
		cve = d["value"]

	return(cve)

def getPolicyIds(data):
	policyIds = ""
	policies = data['policies']

	for policy in policies:
		name = policy["name"]
		id = policy["id"]

		if name == "Security-Critical" or name == "Security-High" or name == "Security-Medium" or name == "Security-Malicious" or name == "License-Banned" or name == "License-None" or name == "License-Copyleft":
			policyIds += "p=" + id + "&"

	result = policyIds.rstrip('&')

	return result


def writeToCsvFile(policyViolations):

	applicationViolations = policyViolations['applicationViolations']

	with open(csvfile, 'w') as fd:
			fd.write("PolicyName,CVE,ApplicationName,OpenTime,Component,Stage\n")
			for applicationViolation in applicationViolations:
				applicationPublicId = applicationViolation["application"]["publicId"]

				policyViolations = applicationViolation["policyViolations"]
				for policyViolation in policyViolations:
					stage = policyViolation["stageId"]
					openTime = policyViolation["openTime"]
					policyName = policyViolation["policyName"]
					packageUrl = policyViolation["component"]["packageUrl"]

					constraintViolations = policyViolation["constraintViolations"]

					for constraintViolation in constraintViolations:
						values = ""

						reasons = constraintViolation["reasons"]
						for reason in reasons:
							v = getCVEValue(reason["reference"])
							values += v+":"
						
						values = values[:-1]
						line = policyName + "," + values + "," + applicationPublicId + "," + openTime + "," + packageUrl + "," + stage + "\n"
						fd.write(line)

	return


def main():
	policies = getNexusIqData('/api/v2/policies')
	policyIds = getPolicyIds(policies)

	policyViolations = getNexusIqData("/api/v2/policyViolations?" + policyIds)
	with open(jsonfile, 'w') as fd:
    		json.dump(policyViolations, fd)

	print(jsonfile)

	writeToCsvFile(policyViolations)

	print(csvfile)
	
if __name__ == '__main__':
	main()
