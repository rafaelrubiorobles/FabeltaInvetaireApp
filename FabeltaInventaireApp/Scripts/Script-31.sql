select 
	case
	when prd.code is null then fspf.code
	else prd.code
	end as code,
	fslf.code as locationCode,
	quantity,
	operation,
	historicdate,
	uif.user_name
from fabeltaschema.productlocationfabeltahistoric fsplfh
	left join product prd on fsplfh.product_id = prd.id
	left join fabeltaschema.productfabelta fspf on fsplfh.product_id = fspf.id
	left join fabeltaschema.locationfabelta fslf on fsplfh.location_id = fslf.id
	left join fabeltaschema.utilisateurinventairefabelta uif on fsplfh.user_id = uif.id where fsplfh.historicdate 
	between to_date('2019/08/07','YYYY/MM/DD') 
	and to_date('2019/08/08','YYYY/MM/DD') 
	order by fsplfh.historicdate asc;