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
                "shortAddress" : "Hitech City, Hyderabad",
                "checkins" : 1844,
                "imageUrl" : "http://www.whitebay.in/images/fusion9.png",
                "rating" : 4.5,
                "isOpen" : true,
                "timings" : "10:30 AM to 12 MIDNIGHT",
                "cuisines" : ["Continental", "Mexican", "North Indian", "Chinese", "Asian", "Thai"],
                "type" : ["Casual Dining"],
                "averageCost" : 1200,
                "longAddress" : "3rd Floor 136/137, Ancis Eco Grand, Near Wipro Lake, Nanakramguda, Financial District, Hitech City, Hyderabad",
                "location" : {
                    "latitude" : 341234445,
                    "longitude" : 334523444
                },
                "previousCheckins" : 8,
                "result" : true,
                "statusCodes" : []
                    
            }