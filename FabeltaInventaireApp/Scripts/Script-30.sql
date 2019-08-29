select 
	else prd.code
	quantity,
from fabeltaschema.productlocationfabeltahistoric fsplfh
	left join product prd on fsplfh.product_id = prd.id
	left join fabeltaschema.productfabelta fspf on fsplfh.product_id = fspf.id
	left join fabeltaschema.locationfabelta fslf on fsplfh.location_id = fslf.id
	left join fabeltaschema.utilisateurinventairefabelta uif on fsplfh.user_id = uif.id where fsplfh.historicdate