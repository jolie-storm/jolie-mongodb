# MongoConnector
This is the Jolie language MongoDB connector 

# Installation

```jpm add @jolie/mongodb```

# Usage
```from @jolie.mongodb.MongoConnector import MongoConnector```

# Operations 

```jolie

interface MongoDBInterface {
  RequestResponse:
  connect (ConnectRequest)(ConnectResponse) throws MongoException ,
  find   (FindRequest)(FindResponse)   throws MongoException JsonParseException ,
  insert  (InsertRequest)(InsertResponse)   throws MongoException JsonParseException IndexDuplicate,
  update  (UpdateRequest)(UpdateResponse)   throws MongoException JsonParseException IndexDuplicate,
  delete  (DelRequest)(DeleteResponse)   throws MongoException JsonParseException ,
  aggregate (AggregateRequest)(AggregateResponse) throws MongoException JsonParseException,
  
}
```

## connect

This operation allows your service to connect to the to your instance of MongoDB
 
 ```jolie
 type ConnectRequest:void{
  .host ?: string
  .dbname : string
  .uri?:string
  .port ?:int
  .timeZone:string
  .jsonStringDebug?:bool
  .logStreamDebug?:bool
  .username?:string
  .password?:string
}
 ```

```jolie 
    connect@mongodb( { host= "localhost" 
                       port=27017 
                       dbname= "test" 
                       username = "test" 
                       password="test" 
                       timeZone ="Europe/Berlin" } )(  )

```

## insert

This operation inserts a new document into a specific collection 

 ```jolie
type InsertRequest:void{
  .collection: string
  .document:undefined
}
 ```

 ```jolie
    insert@mongodb( {collection = "test" 
                     document.nodeA = "nodeA" 
                     document.nodeB = 10 } )(  )
 ```

 ## find 
 This operation allows you to search for documents in a specific collection that match a specific filter 

 ```jolie
 type FindRequest:void{
   .collection: string
   .filter?:undefined
   .projection?:undefined
   .sort?:undefined
   .limit?: int
   .skip?:int
}
```
```jolie
    find@mongodb( {collection = "test"
                  filter = "{nodeB : { $eq : '$nodeB' } }"
                  filter.nodeB= 10
                  projection="{_id:0}" } )( pipelineRequest.data  )
```

in this example we see how to pass a dinamically a parameter to the filter jsonObject 
``` 
filter = "{nodeB : { $eq : '$nodeB' } }"
```
the format to pass parameter is '$parameterName' the paramater value need to be injected by passing as child node of filter node

```jolie
filter.nodeB= 10
```



