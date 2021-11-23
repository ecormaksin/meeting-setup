select
	mt.ミーティングid,
	mt.件名 ,
	mm.メモ ,
	ot.ipアドレス ,
	ot.osファミリー ,
	ot.ユーザーエージェントファミリー ,
	sc.候補日程番号 ,
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
	候補日程.候補日程 sc
		on
	sc.ミーティングid = mt.ミーティングid
where
	mt.ミーティングid = 'a74a9135c79143ab94e092a0d805b4c6'