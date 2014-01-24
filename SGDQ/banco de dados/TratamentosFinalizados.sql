select  
	--ft.idfase,
	(case when ft.idfase = 1 then 1 end) as idfase_1,
	(case when ft.idfase = 2 then 2 end) as idfase_2,
	(case when ft.idfase = 3 then 3 end) as idfase_3,
	--count (t.idtratamento) qtdeTratamento,
	--pa.idpaciente,
	--t.idtratamento,

	ts.nmtratamentostatus,
	--ts.idtratamentostatus,
	f.nmfase
from 
	dbo."Tratamento" as t 
	inner join dbo."TratamentoStatus" as ts on ts.idtratamentostatus = t.idtratamentostatus
	inner join dbo."FaseTratamento" as ft on ft.idtratamento = t.idtratamento
	inner join dbo."Prontuario" as p on p.idprontuario = t.idprontuario
	inner join dbo."Paciente" as pa on pa.idpaciente = p.idpaciente
	inner join dbo."Fase" as f on f.idfase = ft.idfase
where
	t.icativo = 0
	and ft.idfase = (select max(idfase) from dbo."FaseTratamento" where idtratamento = t.idtratamento)
--group by ft.idfase, ts.nmtratamentostatus, f.nmfase
order by ft.idfase
