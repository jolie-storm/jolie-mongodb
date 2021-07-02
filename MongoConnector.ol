type ConnectRequest:void{
  .host ?: string
  .dbname : string
  .port ?:int
  .uri?: string
  .timeZone:string
  .jsonStringDebug?:bool
  .logStreamDebug?:bool
  .username?:string
  .password?:string
}

type ConnectResponse: void

type FindRequest:void{
   .collection: string
   .filter?:undefined
   .projection?:undefined
   .sort?:undefined
   .limit?: int
   .skip?:int
}

type FindResponse:void{
   .document*: undefined
}
type InsertRequest:void{
  .collection: string
  .document:undefined
}

type InsertResponse:void{
  ._id:string{
    ?
  }
}


type UpdateRequest:void{
  .collection: string
  .filter:undefined
  .documentUpdate:undefined
}

type UpdateResponse:undefined

type DelRequest:void{
  .collection: string
  .filter?:undefined
}

type DeleteResponse:void{
  .deleteCounts:long
}

type AggregateRequest:void{
    .collection: string
    .filter*:string{
       ?
    }
}

type AggregateResponse:void{
    .document*:undefined
}

type ListCollectionRequest:undefined

type ListCollectionResponse:void{
  .collection*:string
}
interface MongoDBInterface {
  RequestResponse:
  connect (ConnectRequest)(ConnectResponse) throws MongoException ,
  find   (FindRequest)(FindResponse)   throws MongoException JsonParseException ,
  insert  (InsertRequest)(InsertResponse)   throws MongoException JsonParseException IndexDuplicate,
  update  (UpdateRequest)(UpdateResponse)   throws MongoException JsonParseException IndexDuplicate,
  delete  (DelRequest)(DeleteResponse)   throws MongoException JsonParseException ,
  aggregate (AggregateRequest)(AggregateResponse) throws MongoException JsonParseException,
  listCollection(ListCollectionRequest)(ListCollectionResponse) throws MongoException JsonParseException
}

service MongoConnector {

inputPort ip {
        location:"local"
        interfaces: MongoDBInterface
    }

foreign java {
  class: "joliex.mongodb.MongoDbConnector"
  }
}
