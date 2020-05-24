package main

import(
	//"fmt"
    "net/http"
    "io/ioutil"
	"log"
	//"strings"
	//"encoding/json"
	"os"
)

type ApplicationViolations struct {
	ApplicationViolations []ApplicationViolation `json:"applicationViolations"`
}

type ApplicationViolation struct {
	Application Application `json:"application"`
	PolicyViolations []PolicyViolation `json:"policyViolations"`
}

type Application struct {
	Id string `json:"id"`
	PublicId string `json:"publicId"`
	Name string `json:"name"`
	OrganizationId string `json"organizationId"`
}

type PolicyViolation struct {
	PolicyId string `json:"policyId"`
	PolicyName string `json:"policyName"`
	OpenTime string `json:"openTime"`
	Component Component `json:"component"`
}

type Component struct {
	PackageUrl string `json:"packageUrl"`
}


func main(){

	var iqurl = os.Args[1]
	var iquser = os.Args[2]
	var iqpasswd = os.Args[3]
	var policyIds = os.Args[4]

	var iqapi = iqurl + "/api/v2/policyViolations?" + policyIds

	body := getData(iquser, iqpasswd, iqapi)

	//fmt.Println(string(body))

	homedir := os.Getenv("HOME")
	workdir := homedir + "/nexusiq-metrics"

	os.Mkdir(workdir, 0777)

	jsonFile := workdir + "/policyviolations.json"

	err := ioutil.WriteFile(jsonFile, body, 0644)
	if err != nil {
        panic(err)
    }
}

func getData(username, passwd, url string) []byte {

    client := &http.Client{}

    req, err := http.NewRequest("GET", url, nil)
    req.SetBasicAuth(username, passwd)

    resp, err := client.Do(req)

    if err != nil{
        log.Fatal(err)
    }

    bodyText, err := ioutil.ReadAll(resp.Body)

    //body := string(bodyText)

    return bodyText
}



