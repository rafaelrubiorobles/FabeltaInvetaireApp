select 
	prd.id,
	prd.code,
	pd.value as description,
	prd.active as is_used
from
  	productdescription pd 
  	left join product prd on pd.product_id = prd.id where pd.languagecode like 'fr' and prd.code like 'BER989444/AR';
