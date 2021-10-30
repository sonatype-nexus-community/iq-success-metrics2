import json
import requests
import os
import os.path
import sys

iqurl = sys.argv[1]
iquser = sys.argv[2]
iqpwd = sys.argv[3]

jsonfile = 'componentsinquarantine.json'
csvfile = 'componentsinquarantine.csv'


def get_metrics():
	req = requests.get('{}/api/v2/reports/components/quarantined'.format(iqurl), auth=(iquser, iqpwd), verify=False)
	
	if req.status_code == 200:
		res = req.json()
	else:
		res = "Error fetching data"

	return res


def writeToCsvFile(componentsQuarantined):

	components = componentsQuarantined['componentsInQuarantine']

	with open(csvfile, 'w') as fd:
			fd.write("Repository,Format,PackageUrl,QuarantineTime,PolicyName,ThreatLevel\n")
			for component in components:
				repository = component["repository"]["publicId"]
				format = component["repository"]["format"]

				componentsList = component["components"]
				for comp in componentsList:
					packageUrl = comp["component"]["packageUrl"]
					quarantineTime = comp["component"]["quarantineTime"]

					if "policyViolations" in comp:
						policyViolations = comp["policyViolations"]
						for policyViolation in policyViolations:
							policyName = policyViolation["policyName"]
							threatLevel = policyViolation["threatLevel"]

					line = repository + "," + format + "," + packageUrl + "," + quarantineTime + "," + policyName + "," + str(threatLevel) + "\n"
					fd.write(line)
	
	return


def main():
	componentsQuarantined = get_metrics()

	with open(jsonfile, 'w') as fd:
    		json.dump(componentsQuarantined, fd)
	
	print(jsonfile)

	writeToCsvFile(componentsQuarantined)

	print(csvfile)

				
if __name__ == '__main__':
	main()
