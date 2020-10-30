import json
import requests
import os
import os.path
import sys

iqurl = sys.argv[1]
iquser = sys.argv[2]
iqpwd = sys.argv[3]

outputdir = 'datafiles'

jsonfile = '{}/{}'.format(outputdir, 'applicationevaluations.json')
csvfile = '{}/{}'.format(outputdir, 'applicationevaluations.csv')

def get_metrics():
	req = requests.get('{}/api/v2/reports/applications'.format(iqurl), auth=(iquser, iqpwd), verify=False)
	
	if req.status_code == 200:
		res = req.json()
	else:
		res = "Error fetching data"

	return res


def getApplicationName(urlPath):
	l = urlPath.split('/')
	return(l[3])


def writeToCsvFile(applicationEvaluations):

	with open(csvfile, 'w') as fd:
			fd.write("ApplicationName,EvaluationDate,Stage\n")
			for applicationEvaluation in applicationEvaluations:
				stage = applicationEvaluation["stage"]
				evaluationDate = applicationEvaluation["evaluationDate"]
				applicationId = applicationEvaluation["applicationId"]
				applicationName = getApplicationName(applicationEvaluation["reportDataUrl"])
				line = applicationName + "," + evaluationDate + "," + stage + "\n"
				fd.write(line)
	
	return


def main():
	applicationEvaluations = get_metrics()

	with open(jsonfile, 'w') as fd:
    		json.dump(applicationEvaluations, fd)
	
	print(jsonfile)

	writeToCsvFile(applicationEvaluations)

	print(csvfile)


				
if __name__ == '__main__':
	main()
