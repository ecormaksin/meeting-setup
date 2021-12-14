select
	mt.ミーティングid,
	mt.件名 ,
	mm.メモ ,
	ot.ipアドレス ,
	ot.osファミリー ,
	ot.ユーザーエージェントファミリー ,
	sc.日程候補番号 ,
	sc.開始日時 ,
	sc.終了日時
from
	ミーティング.ミーティング mt
left join
	ミーティング.ミーティングメモ mm
		on
	mm.ミーティングid = mt.ミーティングid
inner join 
	ミーティング.主催者端末 ot
		on
	ot.ミーティングid = mt.ミーティングid
inner join 
	日程候補.日程候補 sc
		on
	sc.ミーティングid = mt.ミーティングid
where
	mt.ミーティングid = 'a74a9135c79143ab94e092a0d805b4c6'
