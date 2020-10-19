import json
import requests
import os
import os.path
import sys

iqurl = sys.argv[1]
iquser = sys.argv[2]
iqpwd = sys.argv[3]
payloadFile = sys.argv[4]


csvfile = 'successmetrics.csv'

def get_payload():
	with open(payloadFile) as rd:
		content = rd.read()
	return content

def get_metrics(payload):

	iqheader = {'Content-Type':'application/json', 'Accept':'text/csv'}

	req = requests.post("{}/api/v2/reports/metrics".format(iqurl), auth=(iquser, iqpwd), verify=False, headers=iqheader, data=payload)
	with open(csvfile, 'wb') as fd:
            for chunk in req.iter_content(chunk_size=1024):
                fd.write(chunk)

	print(csvfile)
	return


def main():
	payload = get_payload()
	get_metrics(payload)

				
if __name__ == '__main__':
	main()
