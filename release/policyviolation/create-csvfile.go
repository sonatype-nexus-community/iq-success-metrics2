package main

import(
	"fmt"
    "io/ioutil"
	"encoding/json"
	"encoding/csv"
	"os"
	"log"
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

	var body []byte

	homedir := os.Getenv("HOME")
	workdir := homedir + "/nexusiq-metrics"
	
	jsonFilePath := workdir + "/policyviolations.json"
	csvFilePath := workdir + "/policyviolations.csv"
	
	// initialise output file 
	csvFile, err := os.Create(csvFilePath)
	checkError("Cannot create output file", err)
	defer csvFile.Close()

	writer := csv.NewWriter(csvFile)

	header := []string{"PolicyName", "ApplicationName", "OpenTime", "Component"}
	err = writer.Write(header)
	checkError("Cannot write to file", err)

	// read inout json file
	jsonFile, err := os.Open(jsonFilePath)

	if err != nil {
		fmt.Println(err)
	}

	defer jsonFile.Close()
	body, _ = ioutil.ReadAll(jsonFile)

	var applicationViolations ApplicationViolations

	json.Unmarshal(body, &applicationViolations)

	for i := 0; i < len(applicationViolations.ApplicationViolations); i++ {
		
		publicId := applicationViolations.ApplicationViolations[i].Application.PublicId

		for j := 0; j < len(applicationViolations.ApplicationViolations[i].PolicyViolations); j++ {
			
			policyName := ""
			openTime := ""
			packageUrl := ""

			policyName = applicationViolations.ApplicationViolations[i].PolicyViolations[j].PolicyName
			openTime = applicationViolations.ApplicationViolations[i].PolicyViolations[j].OpenTime
			packageUrl = applicationViolations.ApplicationViolations[i].PolicyViolations[j].Component.PackageUrl

			csvLine := []string{policyName, publicId,  openTime, packageUrl}

			err = writer.Write(csvLine)
			checkError("Cannot write to file", err)

			writer.Flush()
		}
	}
}


func checkError(message string, err error) {
    if err != nil {
        log.Fatal(message, err)
    }
}





