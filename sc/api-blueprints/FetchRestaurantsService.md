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

    + Body

            {
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
                        "id" : 12345,
                        "name" : "Fusion 9",
                        "address" : "Hitech City, Hyderabad",
                        "checkins" : 250,
                        "imageUrl" : "http://www.whitebay.in/images/fusion9.png",
                        "rating" : 4.5,
                        "rewards" : [
                            "Amazon Gift Cards",
                            "Flipkart Gift Cards"
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
                            "Amazon Gift Cards",
                            "Flipkart Gift Cards",
                            "O2 Spa Coupons",
                            "Direct Discount"
                        ],
                        "status" : true,
                        "statusCodes" : []
                    }
                ]
            }