SELECT fabeltaschema.locationfabelta.code,fabeltaschema.productlocationfabelta.locationquantity FROM fabeltaschema.productfabelta inner join 
fabeltaschema.productlocationfabelta on 
(fabeltaschema.productfabelta.id = fabeltaschema.productlocationfabelta.product_id) inner join
fabeltaschema.locationfabelta on
(fabeltaschema.productlocationfabelta.location_id = fabeltaschema.locationfabelta.id)
and
 fabeltaschema.productfabelta.code = '927457/AN';