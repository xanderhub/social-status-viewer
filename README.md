# social-status-viewer
Social network status viewer

To run the server, cd into the social-status-viewer folder and run:

```
./mvnw spring-boot:run
```

To check social networks status run:

```
http://localhost:3000
```

Example of status response:

```
{
    "instaphoto": [
        {
            "userName": "hipster1",
            "picture": "Food"
        },
        {
            "userName": "hipster2",
            "picture": "Coffee"
        },
        {
            "userName": "hipster3",
            "picture": "Cat"
        },
        {
            "userName": "hipster4",
            "picture": "Tattoo"
        },
        {
            "userName": "hipster5",
            "picture": "Cat with a Tattoo eatting cat food with drinking coffee"
        }
    ],
    "tweety": [
        {
            "userName": "@SomeGuy",
            "tweet": "If you live to be 100, you should make up some fake reason why, just to mess with people... like claim you ate a pinecone every single day."
        },
        {
            "userName": "@UserMcUserName",
            "tweet": "STOP TELLING ME YOUR NEWBORN'S WEIGHT AND LENGTH I DON'T KNOW WHAT TO DO WITH THAT INFORMATION."
        }
    ],
    "bookoffaces": [
        {
            "name": "John Smith",
            "status": "Here's some photos of my holiday. Look how much more fun I'm having than you are!"
        },
        {
            "name": "Sarah Connor",
            "status": "I am in a hospital. I will not tell you anything about why I am here."
        }
    ]
}
```

Example of status response when several social networks unavailable:

```
{
    "instaphoto": {
        "error": "social network status is unavailable"
    },
    "tweety": [
        {
            "userName": "@SomeGuy",
            "tweet": "If you live to be 100, you should make up some fake reason why, just to mess with people... like claim you ate a pinecone every single day."
        },
        {
            "userName": "@UserMcUserName",
            "tweet": "STOP TELLING ME YOUR NEWBORN'S WEIGHT AND LENGTH I DON'T KNOW WHAT TO DO WITH THAT INFORMATION."
        }
    ],
    "bookoffaces": {
        "error": "social network status is unavailable"
    }
}
```
