import json
import requests
import os
import os.path
import sys

iqurl = sys.argv[1]
iquser = sys.argv[2]
iqpwd = sys.argv[3]

jsonfile = 'componentwaivers.json'
csvfile = 'componentwaivers.csv'

def get_metrics():
	req = requests.get('{}/api/v2/reports/components/waivers'.format(iqurl), auth=(iquser, iqpwd), verify=False)
	
	if req.status_code == 200:
		res = req.json()
	else:
		res = "Error fetching data"

	return res


def writeToCsvFile(componentWaivers):

	waiverList = []

	applicationWaivers = componentWaivers['applicationWaivers']
	repositoryWaivers = componentWaivers['repositoryWaivers']

	with open(csvfile, 'w') as fd:
			fd.write("ApplicationName,Stage,PackageUrl,PolicyName,ThreatLevel,Comment\n")
			for waiver in applicationWaivers:
				applicationName = waiver['application']['publicId']
				stages = waiver['stages']
                
				for stage in stages:
					stageId = stage['stageId']
					componentPolicyViolations = stage['componentPolicyViolations']
                    
					for componentPolicyViolation in componentPolicyViolations:
						packageUrl = componentPolicyViolation["component"]["packageUrl"]
						waivedPolicyViolations = componentPolicyViolation['waivedPolicyViolations']

						for waivedPolicyViolation in waivedPolicyViolations:
							policyName = waivedPolicyViolation['policyName']
							threatLevel = waivedPolicyViolation['threatLevel']
							comment = waivedPolicyViolation['policyWaiver']['comment']

							if "\n" in comment:
								comment = comment.replace("\n", "-")

							line = applicationName + "," + stageId + "," + packageUrl + "," + policyName + "," + str(threatLevel) + "," + comment + "\n"
							fd.write(line)
	
	fd.close()

	with open(csvfile, 'a') as fd:
			for waiver in repositoryWaivers:
				name = waiver['repository']['publicId']
				stages = waiver['stages']
               
				for stage in stages:
					stageId = stage['stageId']
					componentPolicyViolations = stage['componentPolicyViolations']
                    
					for componentPolicyViolation in componentPolicyViolations:
						packageUrl = componentPolicyViolation["component"]["packageUrl"]
						waivedPolicyViolations = componentPolicyViolation['waivedPolicyViolations']

						for waivedPolicyViolation in waivedPolicyViolations:
							policyName = waivedPolicyViolation['policyName']
							threatLevel = waivedPolicyViolation['threatLevel']
							comment = waivedPolicyViolation['policyWaiver']['comment']

							line = name + "," + stageId + "," + packageUrl + "," + policyName + "," + str(threatLevel) + "," + comment + "\n"
							fd.write(line)
            
	return


def main():
    componentWaivers = get_metrics()

    with open(jsonfile, 'w') as fd:
    		json.dump(componentWaivers, fd, indent=4)
	
    print(jsonfile)

    writeToCsvFile(componentWaivers)

    print(csvfile)

				
if __name__ == '__main__':
	main()
