# PictKaiwa ディレクトリ構造詳細分析

## 1. バージョン管理システム (.git/)

### Git設定の概要
- リポジトリURL: https://github.com/ikonusuk008/pictkaiwa.git
- デフォルトブランチ: master
- リモート名: origin

### Git設定詳細
```
.git/
├── COMMIT_EDITMSG      # 最後のコミットメッセージ
├── config              # Gitリポジトリ設定ファイル
├── description         # GitWebの説明ファイル
├── FETCH_HEAD          # リモートから取得した最新の参照
├── HEAD               # 現在のブランチポインタ
├── hooks/             # Gitフック用スクリプト
├── index              # Gitのステージングエリア
├── info/              # 除外パターンなどの補助情報
├── logs/              # 参照の更新履歴
├── objects/           # Gitオブジェクトデータベース
├── ORIG_HEAD         # 危険な操作前の HEAD バックアップ
├── packed-refs        # 圧縮された参照データベース
└── refs/              # ブランチとタグの参照
```

### Git設定の特徴
1. Windows環境向け設定
   - filemode = false
   - symlinks = false
   - ignorecase = true

2. リモート設定
   - GitHub上にメインリポジトリが存在
   - フルクローン（非ベア）リポジトリ

## 2. メインプロジェクト構造 (pict_kaiwa_soft/)

### プロジェクト構成ファイル
```
pict_kaiwa_soft/
├── .classpath          # Eclipseのクラスパス定義
├── .project            # Eclipseのプロジェクト定義
├── .settings/          # Eclipse IDE設定
└── eclipse.epf         # Eclipseプリファレンス
```

### アプリケーション実行ファイル
```
├── addpict.exe        # 画像追加ツール
├── kaiwa.exe          # メインアプリケーション
└── setting.exe        # 設定ツール
```

### ビルド設定
```
├── ak.jsmooth        # addpict.exe用ビルド設定
├── kaiwa.jsmooth     # kaiwa.exe用ビルド設定
└── setting.jsmooth   # setting.exe用ビルド設定
```

### プロジェクトリソース
```
├── resource/
│   ├── db/           # データベースファイル
│   ├── help/         # ヘルプドキュメント
│   ├── img/          # 画像リソース
│   ├── oto/          # 音声ファイル
│   └── txt/          # テキストリソース
```

### ソースコード
```
├── src/
│   ├── ekigo_tool/   # 絵記号ツール
│   ├── installer/    # インストーラ
│   ├── main_frame/   # メインウィンドウ
│   ├── pictkaiwa/    # コアロジック
│   ├── statics/      # 静的リソース
│   ├── test/         # テストコード
│   ├── test_tool/    # テストツール
│   └── util/         # ユーティリティ
```

### ドキュメント
```
├── doc/              # プロジェクトドキュメント
├── manual/           # ユーザーマニュアル
└── ReadMe.txt       # 基本情報
```

### ログとデバッグ
```
├── hs_err_pid7856.log  # JVMクラッシュログ
└── service.log         # アプリケーションログ
```

## 特記事項

### 開発環境の特徴
1. Eclipse IDEをメイン開発環境として使用
2. JSmoothを使用してJavaアプリケーションをexe化
3. Gitによるバージョン管理

### プロジェクト構造の特徴
1. モジュール化された設計
   - 機能ごとに分離されたパッケージ構造
   - 明確なリソース管理
   
2. 豊富なドキュメント
   - ユーザーマニュアル
   - ヘルプドキュメント
   - テスト関連ドキュメント

3. テスト環境の整備
   - 専用のテストパッケージ
   - テストツールの存在

4. マルチメディア対応
   - 画像処理機能
   - 音声処理機能
   - リソース管理システム

### ビルドと配布
1. 3つの主要実行ファイル
   - メインアプリケーション (kaiwa.exe)
   - 画像追加ツール (addpict.exe)
   - 設定ツール (setting.exe)

2. 各実行ファイルに対応するJSmoothビルド設定
