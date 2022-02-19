# 開発メモ

## Heroku

### 環境変数の設定

| KEY | VALUE |
| --- | --- |
| TZ | Asia/Tokyo |
| SPRING_DATASOURCE_URL | jdbc:postgresql://＜`DATABASE_URL`に設定されているサーバー名＞:＜`DATABASE_URL`に設定されているポート番号＞/＜`DATABASE_URL`に設定されているDB名＞ |
| SPRING_DATASOURCE_USERNAME | `DATABASE_URL`に設定されているユーザー名 |
| SPRING_DATASOURCE_PASSWORD | `DATABASE_URL`に設定されているパスワード |
