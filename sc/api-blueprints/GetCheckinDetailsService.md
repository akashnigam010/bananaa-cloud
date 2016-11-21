FORMAT: 1A

# Fetch checkin details for business app API
Fetch checkin details for business app based on different parameters

## Group Checkin

## Checkin [/checkin/getCheckinDetails]

Resource to get checkin details

### Get details [POST]

Retrieves the details of a PENDING checkin

+ Request (application/json)

    + Body

            {
                "id" : 123434
            }

+ Response 200 (application/json)

    + Body 

            {
                "checkin" : {
                    "user" : {
                        "id" : 2234543,
                        "name" : "Sanjeev Venkatraman",
                        "primaryImageUrl" : "http://www.whitebay.in/images/sanjeevven_primary.png",
                        "email" : null,
                        "facebookId" : null,
                        "userCheckins" : 34,
                        "checkins" : [
                            {
                                "id" : 876522,
                                "user" : {
                                    "id" : 2234543,
                                    "name" : "Sanjeev Venkatraman",
                                    "userCheckins" : 34,
                                    "secondaryImageUrl" : "http://www.whitebay.in/images/sanjeevven_sec.png"
                                },                        
                                "checkinMessage" : "at Fusion 9 with Kinshuk Mishra, Sunny Arora and Udita Mehrotra",
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
                                    "id" : 2234543,
                                    "name" : "Sanjeev Venkatraman",
                                    "userCheckins" : 34,
                                    "secondaryImageUrl" : "http://www.whitebay.in/images/sanjeevven_sec.png"
                                },  
                                "checkinMessage" : "at Fusion 9 with Vimarsh Kar and Simar Kapoor",
                                "restaurantId" : 12347,
                                "rewardMessage" : "Won an Flipkart Gift Card worth Rs. 100",
                                "rated" : {
                                    "food" : 4,
                                    "service" : 4,
                                    "ambience" : 1,
                                    "net" : 3
                                },
                                "timestamp" : "20161215122634T+0530",
                                "likeCount" : 4
                            },
                            {   "id" : 876522,
                                "user" : {
                                    "id" : 2234543,
                                    "name" : "Sanjeev Venkatraman",
                                    "userCheckins" : 34,
                                    "secondaryImageUrl" : "http://www.whitebay.in/images/sanjeevven_sec.png"
                                },  
                                "checkinMessage" : "at Fusion 9 with Shraddha Saraf and Fariha Rahiman",
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
                    },
                    "previousCheckins" : 3,
                    "card" : 15,
                    "checkinMessage" : "with Raman Rao and Shilpa Reddy",
                    "status" : "PENDING"
                },
                "status" : true,
                "statusCodes" : []
            }


### Get details [POST]

Retrieves the details of an APPROVED checkin

+ Request (application/json)

    + Body

            {
                "id" : 123434
            }

+ Response 200 (application/json)

    + Body 

            {
                "checkin" : {
                    "user" : {
                        "id" : 2234543,
                        "name" : "Sanjeev Venkatraman",
                        "primaryImageUrl" : "http://www.whitebay.in/images/sanjeevven_primary.png",
                        "email" : null,
                        "facebookId" : null,
                        "userCheckins" : 35,
                        "checkins" : [
                            {
                                "id" : 879876,
                                "user" : {
                                    "id" : 2234543,
                                    "name" : "Sanjeev Venkatraman",
                                    "userCheckins" : 35,
                                    "secondaryImageUrl" : "http://www.whitebay.in/images/sanjeevven_sec.png"
                                },                        
                                "checkinMessage" : "at Fusion 9 with Raman Rao and Shilpa Reddy",
                                "restaurantId" : 12345,
                                "rewardMessage" : null,
                                "rated" : null,
                                "timestamp" : "20161219163034T+0530",
                                "likeCount" : 0
                            },{
                                "id" : 876522,
                                "user" : {
                                    "id" : 2234543,
                                    "name" : "Sanjeev Venkatraman",
                                    "userCheckins" : 34,
                                    "secondaryImageUrl" : "http://www.whitebay.in/images/sanjeevven_sec.png"
                                },                        
                                "checkinMessage" : "at Fusion 9 with Kinshuk Mishra, Sunny Arora and Udita Mehrotra",
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
                                    "id" : 2234543,
                                    "name" : "Sanjeev Venkatraman",
                                    "userCheckins" : 34,
                                    "secondaryImageUrl" : "http://www.whitebay.in/images/sanjeevven_sec.png"
                                },  
                                "checkinMessage" : "at Fusion 9 with Vimarsh Kar and Simar Kapoor",
                                "restaurantId" : 12347,
                                "rewardMessage" : "Won an Flipkart Gift Card worth Rs. 100",
                                "rated" : {
                                    "food" : 4,
                                    "service" : 4,
                                    "ambience" : 1,
                                    "net" : 3
                                },
                                "timestamp" : "20161215122634T+0530",
                                "likeCount" : 4
                            },
                            {   "id" : 876522,
                                "user" : {
                                    "id" : 2234543,
                                    "name" : "Sanjeev Venkatraman",
                                    "userCheckins" : 34,
                                    "secondaryImageUrl" : "http://www.whitebay.in/images/sanjeevven_sec.png"
                                },  
                                "checkinMessage" : "at Fusion 9 with Shraddha Saraf and Fariha Rahiman",
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
                    },
                    "previousCheckins" : 4,
                    "card" : 15,
                    "checkinMessage" : "with Raman Rao and Shilpa Reddy",
                    "status" : "APPROVED"
                },
                "status" : true,
                "statusCodes" : []
            }


+ Request (application/json)

    + Body

            {
                "id" : 122345
            }

+ Response 200 (application/json)

    + Body 

            {
                "checkin" : null,
                "status" : false,
                "statusCodes" : [
                    {
                        "errorCode" : 997862,
                        "description" : "Something went wrong, please try again later"
                    }
                ]
            }
