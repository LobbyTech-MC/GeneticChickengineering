# 基因工程雞

![License](https://img.shields.io/github/license/kii-chan-reloaded/GeneticChickengineering) ![Build](https://github.com/kii-chan-reloaded/GeneticChickengineering/workflows/Java%20CI%20with%20Maven/badge.svg)

基因工程雞是一種原先有的資源所實現的雞, 啟發於SetyCz有名的Forge模組Chicken.
兩者的區別主要在於, Chicken具有預定的養育樹, 基因工程雞使用非常基本的模擬遺傳學來確定雞的生長方向
(這意味著你需要查詢在 [Punnett squares](https://en.wikipedia.org/wiki/Punnett_square)).

這是中期向的附加對於
[Slimefun](https://github.com/Slimefun/Slimefun4). 
此外, 它使用Paper-specific(Paper-專用) API 調用, 因此在Spigot伺服器上不起作用.

> 此為**非官方**版本, 請勿在該作者問題追蹤內回報! <br>
> [原作連結](https://github.com/kii-chan-reloaded/GeneticChickengineering) | [非官方Discord](https://discord.gg/GF4CwjFXT9)

| 非官方繁體中文版 | 官方英文版 |
| -------- | -------- |
| 點下方圖片下載 | 點下方圖片下載 |
| [![Build Status](https://xMikux.github.io/builds/SlimeTraditionalTranslation/GeneticChickengineering/master/badge.svg)](https://xMikux.github.io/builds/SlimeTraditionalTranslation/GeneticChickengineering/master) | [![Build Status](https://thebusybiscuit.github.io/builds/kii-chan-reloaded/GeneticChickengineering/master/badge.svg)](https://thebusybiscuit.github.io/builds/kii-chan-reloaded/GeneticChickengineering/master) |

## 插件基礎
*查看*
[*wiki*](https://github.com/kii-chan-reloaded/GeneticChickengineering/wiki)
*來了解更多詳細訊息*

主世界的雞幾乎已完全占主導地位的"正常"雞
今天我們還是知道與愛著, 但是有些雞具有潛在力
有了正確的工具, 時間和照顧, 這項潛在力量可能
會被發現, 並釋放出雞的真正潛力!

對雞的基因改良第一步是製造雞網
讓它變成袋子雞. 下一步, 建造一個基因測序儀來分析袋子雞的基因種類,
當你收集了兩隻或更多良好的雞時,合成私人雞舍然後讓雞做它的事業.
過了一些時間, 它們會骰出基因骰子並生出一個孩子!
從那, 釋放小雞讓他長大, 然後循環
繼續.

最終, 你的努力將得到一種特殊的雞, 該雞可以產生資源!
但是, 它們不能獨自做到這點... 這就是鼓舞室的工作時間. 放入至少一隻雞
純合隱性等位基因(一組兩個小寫字母) 它們金開始生產資源!
並非所有雞生產的資源速度都相同. 由於受到來自雜合等位基因對(一顯性和一隱性),
只有純基因對會比雜基因對還要快. 在鼓舞室安裝一些物流節點, 以保持穩定,
永無休止地提供不同的資源!

查看 [序列表](https://github.com/kii-chan-reloaded/GeneticChickengineering/wiki/Sequencing-Guide)
或在遊戲中的Slimefun指南來了解如何製作特定的資源,
或者開始瘋狂地繁殖雞直到你有所有64個品種

此為**非官方**版本, 請勿在該作者的問題追蹤內回報! <br>
且翻譯有很多處讀起來很怪的 歡迎修正! <br>
[原專案連結](https://github.com/kii-chan-reloaded/GeneticChickengineering)

## 圖片

![A basic machine overview](/images/gce_machines.png)

------

![A baby chicken fresh out of the Genetic Sequencer](/images/gce_genseq.png)

------

![An experience chicken working](/images/gce_excham.png)

------

在默認情況下, 雞將在顯示它的資源在自定義名稱前與顯示其雞的基因序. 
如果雞已經有一個特殊名稱, 那資源將會被附加添加至該名稱的尾部. 
這項特徵可以在插件的`config.yml`中關閉.

![A nether quartz chicken named Crystal](/images/gce_names.png)

## Thanks

None of this would be possible if it weren't for TheBusyBiscuit's hard work on
Slimefun, as well as the tremendously helpful Developer's Guide. Furthermore, a 
significant portion of this addon uses similar or identical code from
MobCapturer, and this project would not have gotten off the ground without it.

## Issues/Requests

If you have any issues or feature requests, feel free to open an issue about it.
If you're experiencing bugs, please provide any relevent server logs in your
issue.
