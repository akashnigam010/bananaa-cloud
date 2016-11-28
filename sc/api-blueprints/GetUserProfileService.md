FORMAT: 1A

# Fetch User Profile for customer app API
Fetch user profile for customer app based on different parameters

## Group User

## User [/user/getUserProfile]

Resource to get user profile

### Get profile [POST]

Retrieves the user profile

+ Request (application/json)

    + Body

            {
                "id" : 2234543
            }

+ Response 200 (application/json)

    + Body 

            {
                "user" : {
                    "id" : 2234543,
                    "name" : "Sanjeev Venkatraman",
                    "primaryImageUrl" : "http://www.whitebay.in/images/sanjeevven_primary.png",
                    "email" : "sanjeev.venkat1990@gmail.com",
                    "facebookId" : "sanjeev.vekat.35",
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
                },
                "result" : true,
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
                "user" : null,
                "result" : false,
                "statusCodes" : [
                    {
                        "code" : 997862,
                        "description" : "Something went wrong, please try again later"
                    }
                ]
            }
