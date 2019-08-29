SELECT fabeltaschema.productfabelta.code, fabeltaschema.locationfabelta.code as codelocation, fabeltaschema.productlocationfabelta.locationquantity, fabeltaschema.productlocationfabelta.commentaire FROM fabeltaschema.productfabelta inner join fabeltaschema.productlocationfabelta on 
(fabeltaschema.productfabelta.id = fabeltaschema.productlocationfabelta.product_id) inner join fabeltaschema.locationfabelta on 
(fabeltaschema.productlocationfabelta.location_id = fabeltaschema.locationfabelta.id)