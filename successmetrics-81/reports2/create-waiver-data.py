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
            fd.write("ApplicationName,Stage,PackageUrl,PolicyName,ThreatLevel,Comment,CreateDate,ExpiryTime\n")
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

                            if "createTime" in waivedPolicyViolation['policyWaiver'].keys():
                                createDate = waivedPolicyViolation['policyWaiver']['createTime']

                                if "expiryTime" in waivedPolicyViolation['policyWaiver'].keys():
                                    expiryTime = waivedPolicyViolation['policyWaiver']['expiryTime']
                                else:
                                    expiryTime = "non-expiring"

                            else:
                                createDate = "needs re-eval"
                                expiryTime = "needs re-eval"
        
                            if comment is not None:
                                if "\n" in comment:
                                    comment = comment.replace("\n", "-")

                                if "," in comment:
                                    comment = comment.replace(",", "|")
                            else:
                                comment = ""
                                
                            createDate =  formatDate(createDate)
                            expiryTime =  formatDate(expiryTime)

                            line = applicationName + "," + stageId + "," + packageUrl + "," + policyName + "," + str(threatLevel) + "," + comment + "," + createDate + "," + expiryTime + "\n"
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

                            if "createTime" in waivedPolicyViolation['policyWaiver'].keys():
                                createDate = waivedPolicyViolation['policyWaiver']['createTime']

                                if "expiryTime" in waivedPolicyViolation['policyWaiver'].keys():
                                    expiryTime = waivedPolicyViolation['policyWaiver']['expiryTime']
                                else:
                                    expiryTime = "non-expiring"

                            else:
                                createDate = "needs re-eval"
                                expiryTime = "n/a"

                            createDate =  formatDate(createDate)
                            expiryTime =  formatDate(expiryTime)

                            line = name + "," + stageId + "," + packageUrl + "," + policyName + "," + str(threatLevel) + "," + comment + "," + createDate + "," + expiryTime + "\n"
                            fd.write(line)
            
    return

def formatDate(dt):
  d = dt[0:10]
  # d = d.replace("T", ".")
  return d


def readJsonFileInstead():
    f = open('componentwaivers.json')
    data = json.load(f)
    return data

def main():
    componentWaivers = get_metrics()
    # componentWaivers = readJsonFileInstead()

    with open(jsonfile, 'w') as fd:
            json.dump(componentWaivers, fd, indent=4)

    print(jsonfile)

    writeToCsvFile(componentWaivers)

    print(csvfile)


if __name__ == '__main__':
    main()
