FORMAT: 1A

# Fetch My Profile for customer app API
Fetch my profile for customer app

## Group User

## User [/user/getMyProfile]

Resource to get my profile

### Get my profile [GET]

Retrieves my profile


+ Response 200 (application/json)

    + Body 

            {
                "user" : {
                    "id" : 2234543,
                    "name" : "Rajiv Sehgal",
                    "primaryImageUrl" : "http://www.whitebay.in/images/rajivsehga_primary.png",
                    "email" : "rajiv.sehgal2@gmail.com",
                    "facebookId" : "rajiv.sehgal.43",
                    "userCheckins" : 34
                },
                "result" : true,
                "statusCodes" : []
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
