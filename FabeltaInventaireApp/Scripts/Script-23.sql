select 
	case
	when prd.code is null then fspf.code
	else prd.code
	end as code,
	fslf.code as locationCode,
	locationquantity,
	commentaire 
from 
	fabeltaschema.productlocationfabelta fsplf
	left join product prd on fsplf.product_id = prd.id
	left join fabeltaschema.productfabelta fspf on fsplf.product_id = fspf.id
	left join fabeltaschema.locationfabelta fslf on fsplf.location_id = fslf.id