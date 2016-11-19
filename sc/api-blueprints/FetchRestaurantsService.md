FORMAT: 1A

# Fetch Restaurants API
Fetch list of restaurants based on location and promotional priority 

The list must be paginated in chucks of 10 restaurants.

## Group Restaurant

## Restaurant [/restaurant/getRestaurants]

Resource to get restaurant information

### Get list of restaurants [POST]

Retrieves the list of restaurant with given parameters

+ Request (application/json)

        {
            "token" : "abc123abc",
            "location" : {
                "longitude" : 001234445,
                "lattitude" : 334523444
            }
        }

+ Response 200 (application/json)

    + Body 

            {
                [
                    {
                        "id" : 12345,
                        "name" : "Fusion 9",
                        "address" : "Hitech City, Hyderabad",
                        "checkins" : 250,
                        "imageUrl" : "http://www.whitebay.in/images/fusion9.png",
                        "rating" : 4.5,
                        "rewards" : [
                            {
                                "id" : 1223,
                                "description" : "Amazon Gift Card worth Rs. 100"
                            },
                            {
                                "id" : 1224,
                                "description" : "Flipkart Gift Card worth Rs. 100"
                            }
                        ],
                        "status" : true,
                        "statusCodes" : []
                    },
                    {
                        "id" : 12346,
                        "name" : "Heartcup Cafe' Coffee",
                        "address" : "Kondapur, Hyderabad",
                        "checkins" : 136,
                        "imageUrl" : "http://www.whitebay.in/images/heartcupcafecoffee.png",
                        "rating" : 4.2,
                        "rewards" : [
                            {
                                "id" : 1223,
                                "description" : "Amazon Gift Card worth Rs. 100"
                            },
                            {
                                "id" : 1224,
                                "description" : "Flipkart Gift Card worth Rs. 100"
                            }
                        ],
                        "status" : true,
                        "statusCodes" : []
                    }
                ]
            }