package main

import(
	"fmt"
    "net/http"
    "io/ioutil"
	"log"
	"strings"
	"encoding/json"
	"os"
)

type Policies struct {
	Policies []Policy `json:"policies"`
}

type Policy struct {
	Id string `json:"id"`
	Name string `json:"name"`
	OwnerId string `json:"ownerId"`
	PolicyType string `json:"policyType"`
}

var  policyIdsStr string

func init(){
	policyIdsStr = ""
}

func main(){

	var iqurl = os.Args[1]
	var iquser = os.Args[2]
	var iqpasswd = os.Args[3]

	var iqapi = iqurl + "/api/v2/policies"

	body := getData(iquser, iqpasswd, iqapi)

	var policies Policies

	json.Unmarshal(body, &policies)

	for i := 0; i < len(policies.Policies); i++ {
		policyName := policies.Policies[i].Name
		policyId := policies.Policies[i].Id
		ownerId := policies.Policies[i].OwnerId
		policyType := policies.Policies[i].PolicyType

		fmt.Println(policyName + "," + policyId + "," + ownerId  + "," + policyType)

		switch strings.ToLower(policyName) {
			case "security-critical": policyIdsStr = policyIdsStr + "p=" + policyId + "&"
			case "security-high": policyIdsStr = policyIdsStr + "p=" + policyId + "&"
			case "security-medium": policyIdsStr = policyIdsStr + "p=" + policyId + "&"		
		}
	}

	policyIdsStr = strings.TrimRight(policyIdsStr, "&")

	fmt.Println("\n" + policyIdsStr + "\n")
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

    // body := string(bodyText)

    return bodyText
}



