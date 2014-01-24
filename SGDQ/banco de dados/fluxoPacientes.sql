SELECT * FROM generate_series(DATE'2012-01-01',DATE'2012-12-31',INTERVAL'1 month');
-- CURRENT_TIMESTAMP

select  
	t.idtratamento,
	t.dtinclusao,
	t.dttratamentofim
	--trim(trim(to_char(t.dtinclusao, 'month')) || '/' || DATE_PART('year', t.dtinclusao)) as dtinclusao,
	--trim(trim(to_char(t.dttratamentofim, 'month')) || '/' || DATE_PART('year', t.dttratamentofim)) as dttratamentofim
from 
	dbo."Tratamento" as t 
--group by t.dtinclusao, t.dttratamentofim
