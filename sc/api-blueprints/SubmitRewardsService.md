FORMAT: 1A

# Submit rewards for business app API
Submit rewards for business app

## Group Reward

## Reward [/reward/submitRewards]

Resource to submit list of rewards to a customer for a checkin

### Submmit rewards [POST]

Submits a list of rewards in a restaurant for a customer against a checkin

+ Request (application/json)

        {
            "rewards" : [
                {
                    "id" : 4454,
                    "type" : "COUPON",
                    "quantity" : 1,
                    "value" : null
                },
                {
                    "id" : 4455,
                    "type" : "COUPON",
                    "quantity" : 2,
                    "value" : null
                },
                {
                    "id" : 9999,
                    "type" : "DIRECT",
                    "quantity" : null,
                    "value" : 150.00
                },
                {
                    "id" : 9999,
                    "type" : "PERCENT",
                    "quantity" : null,
                    "value" : 5.50
                }
            ],
            "checkinId" : 2345
        }

+ Response 200 (application/json)

    + Body 

            {
                
                "result" : true,
                "statusCodes" : []
            }

+ Request (application/json)

        {
            "rewards" : [
                {
                    "id" : 4454,
                    "type" : "COUPON",
                    "quantity" : 1,
                    "value" : null
                },
                {
                    "id" : 4455,
                    "type" : "COUPON",
                    "quantity" : 2,
                    "value" : null
                },
                {
                    "id" : 9999,
                    "type" : "DIRECT",
                    "quantity" : null,
                    "value" : 150.00
                },
                {
                    "id" : 9999,
                    "type" : "PERCENT",
                    "quantity" : null,
                    "value" : 5.50
                }
            ],
            "checkinId" : 2345
        }

+ Response 200 (application/json)

    + Body 

            {
                "result" : false,
                "statusCodes" : [
                    {
                        "code" : 997862,
                        "description" : "Something went wrong, please try again later"
                    }
                ]
            }
