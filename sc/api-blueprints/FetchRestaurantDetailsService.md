FORMAT: 1A

# Fetch Restaurant Details API
Fetch restaurant details based on id

## Group Restaurant

## Restaurant [/restaurant/getRestaurantDetails]

Resource to get restaurant information

### Get restaurant information [POST]

Retrieves the restaurant information with given id

+ Request (application/json)

        {
            "id" : 12345
        }

+ Response 200 (application/json)

    + Body 

            {
                
                "id" : 12345,
                "name" : "Fusion 9",
                "address" : "Hitech City, Hyderabad",
                "checkins" : 250,
                "imageUrl" : "http://www.whitebay.in/images/fusion9.png",
                "rating" : 4.5,
                "rewards" : [                    
                    "Amazon Gift Cards",
                    "Flipkart Gift Cards",
                    "O2 Spa Coupons",
                    "Goodie Bags",
                    "VLCC Coupons",
                    "Direct Discount"
                ],
                "previousCheckins" : 8,
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
                            "ambience" : 4,
                            "net" : 4
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
                        "checkinMessage" : "with Vimarsh Kar and Simar Kapoor",
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
                        "checkinMessage" : "with Shraddha Saraf and Fariha Rahiman",
                        "rewardMessage" : "Won an exciting Goodie bag",
                        "rated" : {
                            "food" : 4,
                            "service" : 4,
                            "ambience" : 1,
                            "net" : 3
                        },
                        "timestamp" : "20161215113454T+0530",
                        "likeCount" : 19
                    }
                ],
                "status" : true,
                "statusCodes" : []
                    
            }