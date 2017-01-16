FORMAT: 1A

# Fetch Checkins for business app API
Fetch checkins for business app based on different parameters

## Group Checkin

## Checkin [/checkin/getCheckinsForBusiness]

Resource to get checkin information

### Get checkins [POST]

Retrieves the list of checkins in chunk of 10
- HISTORY returns checkins with status - REWARD_SUBMITTED and RATED
- CURRENT returns checkins with status - PENDING and APPROVED

+ Request (application/json)

    + Body

            {
                "type" : "HISTORY"
            }

+ Response 200 (application/json)

    + Body 

            {
                "checkins" : [
                    {
                        "id" : 876522,
                        "user" : {
                            "id" : 344534,
                            "name" : "Vikarm Chaudhary",
                            "userCheckins" : 34,
                            "secondaryImageUrl" : "http://www.whitebay.in/images/vikarmchau_sec.png"
                        },                        
                        "checkinMessage" : "with Kinshuk Mishra, Sunny Arora and Udita Mehrotra",
                        "rewardMessage" : "Won an Amazon Gift Card worth Rs. 100",
                        "rated" : {
                            "food" : 4,
                            "service" : 4,
                            "ambience" : 1,
                            "net" : 3
                        },
                        "card" : 15,
                        "status" : "RATED",
                        "previousCheckins" : null,
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
                        "checkinMessage" : "with Vimarsh Kar and Simar Kapoor",
                        "rewardMessage" : "Won an Flipkart Gift Card worth Rs. 100",
                        "rated" : null,
                        "card" : 10,
                        "status" : "REWARD_SUBMITTED",
                        "previousCheckins" : null,
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
                        "checkinMessage" : "with Shraddha Saraf and Fariha Rahiman",
                        "rewardMessage" : "Won an exciting Goodie bag",
                        "rated" : {
                            "food" : 4,
                            "service" : 4,
                            "ambience" : 4,
                            "net" : 4
                        },
                        "card" : 2,
                        "status" : "RATED",
                        "previousCheckins" : null,
                        "timestamp" : "20161215113454T+0530",
                        "likeCount" : 19
                    }
                ],
                "result" : true,
                "statusCodes" : []
            }


+ Request (application/json)

    + Body

            {
                "type" : "CURRENT"
            }

+ Response 200 (application/json)

    + Body 

            {
                "checkins" : [
                    {
                        "id" : 999999,
                        "user" : {
                            "id" : 344534,
                            "name" : "Vikarm Chaudhary",
                            "userCheckins" : 34,
                            "secondaryImageUrl" : "http://www.whitebay.in/images/vikarmchau_sec.png"
                        },                        
                        "checkinMessage" : "with Kinshuk Mishra, Sunny Arora and Udita Mehrotra",
                        "rewardMessage" : null,
                        "rated" : null,
                        "card" : 15,
                        "status" : "PENDING",
                        "previousCheckins" : 3,
                        "timestamp" : "20161219163034T+0530",
                        "likeCount" : null
                    },
                    {   
                        "id" : 876523,
                        "user" : {
                            "id" : 344544,
                            "name" : "Sanjeev Venkatraman",
                            "userCheckins" : 13,
                            "secondaryImageUrl" : "http://www.whitebay.in/images/sanjeevven_sec.png"
                        },
                        "checkinMessage" : "with Vimarsh Kar and Simar Kapoor",
                        "rewardMessage" : null,
                        "rated" : null,
                        "card" : 10,
                        "status" : "APPROVED",
                        "previousCheckins" : 2,
                        "timestamp" : "20161215122634T+0530",
                        "likeCount" : null
                    }
                ],
                "result" : true,
                "statusCodes" : []
            }


+ Request (application/json)

    + Body

            {
                "type" : "HISTORY"
            }

+ Response 200 (application/json)

    + Body 

            {
                "checkins" : null,
                "result" : false,
                "statusCodes" : [
                    {
                        "code" : 997862,
                        "description" : "Something went wrong, please try again later"
                    }
                ]
            }


+ Request (application/json)

    + Body

            {
                "type" : "CURRENT"
            }

+ Response 200 (application/json)

    + Body 

            {
                "checkins" : null,
                "result" : false,
                "statusCodes" : [
                    {
                        "code" : 997862,
                        "description" : "Something went wrong, please try again later"
                    }
                ]
            }