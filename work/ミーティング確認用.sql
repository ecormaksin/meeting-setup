select
	mt.ミーティング番号,
	mt.件名 ,
	mm.メモ ,
	ot.ipアドレス ,
	ot.osファミリー ,
	ot.ユーザーエージェントファミリー ,
	sc.日程番号 ,
	sc.開始日時 ,
	sc.終了日時
from
	ミーティング._ミーティング mt
left join
	ミーティング._ミーティングメモ mm
		on
	mm.ミーティング番号 = mt.ミーティング番号
inner join 
	ミーティング.主催者端末 ot
		on
	ot.ミーティング番号 = mt.ミーティング番号
inner join 
	日程候補.日程 sc
		on
	sc.ミーティング番号 = mt.ミーティング番号
where
	mt.ミーティング番号 = 'a74a9135c79143ab94e092a0d805b4c6'
