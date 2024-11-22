@echo off
:: Die Folgenden einstellungen kann man entweder hier als Preset einstellen, oder jedes mal manuell mit CLICK_ME.bat konfigurieren.
::
:: Das erste  Argument bezieht sich auf die normale Ausgabe der Zeit.
:: Das zweite Argument bezieht sich auf die Statusleiste.
:: Das dritte Argument bezieht sich darauf, ob Das Terminal nach jeder Ausgabe sauber gemacht werden soll.
:: Das vierte Argument bezieht sich auf das minimale-Intervall in Sekunden.
set normaleAusgabe=true
set printStatusBar=true
set clearAfterPrint=true
set delayInSekunden=0.5
:: ------------------------
:: Ab hier nichts Anfassen!
set fensterTitel=DisplayDayPercentage
title %fensterTitel%
powershell -ExecutionPolicy Bypass -File SetTopMost.ps1 -WindowTitle "%fensterTitel%"
set lines=30
if "%clearAfterPrint%"=="true" (
    if "%normaleAusgabe%"=="true" (
        if "%printStatusBar%"=="true" (
            set lines=3
        ) else (
            set lines=2
        )
    ) else (
        if "%printStatusBar%"=="true" (
            set lines=2
        ) else (
            set lines=1
        )
    )
)
mode con: cols=27 lines=%lines%
java -jar DisplayDayPercentage.jar %normaleAusgabe% %printStatusBar% %clearAfterPrint% %delayInSekunden%