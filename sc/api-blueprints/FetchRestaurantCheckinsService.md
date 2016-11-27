FORMAT: 1A

# Fetch Restaurant Checkins API
Fetch restaurant checkins based on id and page number

## Group Restaurant

## Restaurant [/restaurant/getRestaurantCheckins]

Resource to get restaurant checkins

### Get restaurant checkins [POST]

Retrieves the restaurant checkins with given id and page number

+ Request (application/json)

        {
            "id" : 12345,
            "page" : 1
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
                            "imageUrl" : "http://www.whitebay.in/images/vikarmchau_sec.png"
                        },
                        "taggedUsers" : [
                            {
                                "id" : 2331412,
                                "name" : "Kinshuk Mishra"
                            },
                            {
                                "id" : 4314123,
                                "name" : "Sunny Arora"
                            },
                            {
                                "id" : 2341423,
                                "name" : "Udita Mehrotra"
                            }
                        ],                  
                        "rewardMessage" : "Won an Amazon Gift Card worth Rs. 100",
                        "rating" : 4.2,
                        "timestamp" : "20161219163034T+0530",
                        "likeCount" : 21
                    },
                    {   
                        "id" : 876523,
                        "user" : {
                            "id" : 344544,
                            "name" : "Sanjeev Venkatraman",
                            "userCheckins" : 13,
                            "imageUrl" : "http://www.whitebay.in/images/sanjeevven_sec.png"
                        },
                        "taggedUsers" : [
                            {
                                "id" : 1123123,
                                "name" : "Vimarsh Kar"
                            },
                            {
                                "id" : 3454554,
                                "name" : "Simar Kapoor"
                            }
                        ],
                        "rewardMessage" : "Won an Flipkart Gift Card worth Rs. 100",
                        "rated" : 4.0,
                        "timestamp" : "20161215122634T+0530",
                        "likeCount" : 4
                    },
                    {   
                        "id" : 876522,
                        "user" : {
                            "id" : 344526,
                            "name" : "Vaibhav Gupta",
                            "userCheckins" : 4,
                            "imageUrl" : "http://www.whitebay.in/images/vaibhavgup_sec.png"
                        },
                        "taggedUsers" : [
                            {
                                "id" : 1123128,
                                "name" : "Shraddha Saraf"
                            },
                            {
                                "id" : 3454556,
                                "name" : "Fariha Rahiman"
                            }
                        ],
                        "rewardMessage" : "Won an exciting Goodie bag",
                        "rated" : 3.8,
                        "timestamp" : "20161215122634T+0530",
                        "likeCount" : 19
                    }
                ],
                "status" : true,
                "statusCodes" : []
                    
            }