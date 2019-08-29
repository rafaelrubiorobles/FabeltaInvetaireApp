select
	case
	when prd.code is null then fspf.code
	else prd.code
	end as code,
		locationquantity
from 
	fabeltaschema.productlocationfabelta plfb
	left join fabeltaschema.productfabelta fspf on plfb.product_id = fspf.id
	left join product prd on plfb.product_id = prd.id
	left join fabeltaschema.locationfabelta fslf on plfb.location_id = fslf.id where fslf.code = 'EN-A1'

	