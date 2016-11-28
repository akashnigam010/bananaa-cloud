FORMAT: 1A

# Fetch rewards for business app API
Fetch rewards for business app

## Group Reward

## Reward [/reward/getRewards]

Resource to get list of rewards in a restaurant

### Get rewards [GET]

Retrieves the list of rewards in a restaurant 


+ Response 200 (application/json)

    + Body 

            {
                "rewards" : [
                    {
                        "id" : 4454,
                        "shortDescription" : "Amazon Gift Cards",
                        "longDescription" : "Amazon gift card worth Rs. 100"
                    },
                    {
                        "id" : 4455,
                        "shortDescription" : "Flipkart Gift Cards",
                        "longDescription" : "Flipkart gift card worth Rs. 100"
                    },
                    {
                        "id" : 4456,
                        "shortDescription" : "O2 Spa Coupons",
                        "longDescription" : "O2 spa coupons worth Rs. 100"
                    },
                    {
                        "id" : 4457,
                        "shortDescription" : "Goodie Bag",
                        "longDescription" : "An exciting goodie bag"
                    },
                    {
                        "id" : 9999,
                        "shortDescription" : "Discount",
                        "longDescription" : "Direct discount"
                    }
                ],
                "result" : true,
                "statusCodes" : []
            }


+ Response 200 (application/json)

    + Body 

            {
                "rewards" : null,
                "result" : false,
                "statusCodes" : [
                    {
                        "code" : 997862,
                        "description" : "Something went wrong, please try again later"
                    }
                ]
            }
