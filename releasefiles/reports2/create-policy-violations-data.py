import json
import requests
import os
import os.path
import sys
import csv

iqurl = sys.argv[1]
iquser = sys.argv[2]
iqpwd = sys.argv[3]

jsonfile = 'policyviolations.json'
csvfile = 'policyviolations.csv'

securityPolicyList = []
securityPolicyList.append("Security-Critical")
securityPolicyList.append("Security-High")
securityPolicyList.append("Security-Medium")
securityPolicyList.append("Security-Malicious")
securityPolicyList.append("Security-Namespace Conflict")
securityPolicyList.append("Integrity-Rating")

licensePolicyList = []
licensePolicyList.append("License-Banned")
licensePolicyList.append("License-None")
licensePolicyList.append("License-Copyleft")

def getNexusIqData(api):
	url = "{}{}" . format(iqurl, api)

	req = requests.get(url, auth=(iquser, iqpwd), verify=False)

	if req.status_code == 200:
		res = req.json()
	else:
		res = "Error fetching data"

	return res

def getLicense(reasons):
	licenses = []
	licenseList  = ""

	for reason in reasons:
		licenseString = reason["reason"]

		fstart = licenseString.find('(')
		fend = licenseString.find(')')

		license = licenseString[fstart:fend]
		license = license[2:-1]

		if not license in licenses:
			licenses.append(license)

	for l in licenses:
		licenseList += l+":"

	licenseList = licenseList[:-1]

	return(licenseList)


def getCVE(reasons):
	cves = []
	cveList = ""

	for reason in reasons:
		reference = reason["reference"]

		if type(reference) is dict:
			cve = reference["value"]

			if not cve in cves:
				cves.append(cve)

	for c in cves:
		cveList += c+":"

	cveList = cveList[:-1]

	return(cveList)

def getPolicyIds(data):
    
	policyIds = ""
	policies = data['policies']

	for policy in policies:
		name = policy["name"]
		id = policy["id"]

		if name in securityPolicyList or name in licensePolicyList:
			policyIds += "p=" + id + "&"

	result = policyIds.rstrip('&')

	return result


def writeToCsvFile(policyViolations):

	applicationViolations = policyViolations['applicationViolations']

	with open(csvfile, 'w') as fd:
			writer = csv.writer(fd)

			line = []
			line.append("PolicyName")
			line.append("Reason")
			line.append("ApplicationName")
			line.append("OpenTime")
			line.append("Component")
			line.append("Stage")

			writer.writerow(line)

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
						reason = ""

						reasons = constraintViolation["reasons"]

						if policyName == "Integrity-Rating":
							reason = "Integrity-Rating"
						elif policyName in securityPolicyList:
							reason = getCVE(reasons)
						elif policyName in licensePolicyList:
							reason = getLicense(reasons)
						else:
							reason = ""

						line = []
						line.append(policyName)
						line.append(reason)
						line.append(applicationPublicId)
						line.append(openTime)
						line.append(packageUrl)
						line.append(stage)

						writer.writerow(line)

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
