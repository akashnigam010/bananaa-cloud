FORMAT: 1A

# Fetch Checkins for customer app API
Fetch checkins for customer app based on different parameters

## Group Checkin

## Checkin [/checkin/getCheckins]

Resource to get checkin information

### Get checkins [POST]

Retrieves the list of checkins in chunk of 10

+ Request (application/json)

    + Body

            {
                "type" : "AROUNDME",
                "location" : {
                    "longitude" : 341234445,
                    "lattitude" : 334523444
                }
            }

+ Response 200 (application/json)

    + Body 

            {
                [
                    {
                        "id" : 876522,
                        "user" : {
                            "id" : 344534,
                            "name" : "Vikarm Chaudhary",
                            "userCheckins" : 34,
                            "secondaryImageUrl" : "http://www.whitebay.in/images/vikarmchau_sec.png"
                        },                        
                        "checkinMessage" : "at Whiteboard Cafe with Kinshuk Mishra, Sunny Arora and Udita Mehrotra",
                        "restaurantId" : 12345,
                        "rewardMessage" : "Won an Amazon Gift Card worth Rs. 100",
                        "rated" : {
                            "food" : 4,
                            "service" : 4,
                            "ambience" : 1,
                            "net" : 3
                        },
                        "timestamp" : "20161219163034T+0530",
                        "likeCount" : 21
                    },
                    {   
                        "id" : 876523,
                        "user" : {
                            "id" : 344544,
                            "name" : "Sanjeev Venkatraman",
                            "userCheckins" : 13,
                            "secondaryImageUrl" : "http://www.whitebay.in/images/sanjeevven_sec.png"
                        },
                        "checkinMessage" : "at Fusion 9 with Vimarsh Kar and Simar Kapoor",
                        "restaurantId" : 12347,
                        "rewardMessage" : "Won an Flipkart Gift Card worth Rs. 100",
                        "rated" : null,
                        "timestamp" : "20161215122634T+0530",
                        "likeCount" : 4
                    },
                    {   "id" : 876522,
                        "user" : {
                            "id" : 344526,
                            "name" : "Vaibhav Gupta",
                            "userCheckins" : 4,
                            "secondaryImageUrl" : "http://www.whitebay.in/images/vaibhavgup_sec.png"
                        },
                        "checkinMessage" : "at Heartcup Cafe Coffee with Shraddha Saraf and Fariha Rahiman",
                        "restaurantId" : 12348,
                        "rewardMessage" : "Won an exciting Goodie bag",
                        "rated" : {
                            "food" : 4,
                            "service" : 4,
                            "ambience" : 4,
                            "net" : 4
                        },
                        "timestamp" : "20161215113454T+0530",
                        "likeCount" : 19
                    }
                ]
            }