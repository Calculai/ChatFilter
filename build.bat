@echo off
setlocal
cd /d "%~dp0"

set "PROJECT=%~dp0project"
set "OUTPUT=%~dp0output"

if not exist "%PROJECT%\gradlew.bat" (
    echo ERROR: project\gradlew.bat was not found.
    goto failed
)

echo Building Chat Filter...
call "%PROJECT%\gradlew.bat" -p "%PROJECT%" clean build
if errorlevel 1 goto failed

if not exist "%OUTPUT%" mkdir "%OUTPUT%"
del /q "%OUTPUT%\chatfilter-*.jar" 2>nul

set "FOUND="

for %%F in ("%PROJECT%\build\libs\chatfilter-*.jar") do (
    if exist "%%~fF" (
        echo %%~nF | findstr /I /C:"-sources" >nul
        if errorlevel 1 (
            copy /Y "%%~fF" "%OUTPUT%\" >nul
            if errorlevel 1 goto failed
            echo.
            echo SUCCESS: output\%%~nxF
            set "FOUND=1"
        )
    )
)

if not defined FOUND goto nojar

echo.
pause
exit /b 0

:nojar
echo ERROR: Build completed, but no mod JAR was found.
goto failed

:failed
echo.
echo Build failed. Read the errors above.
pause
exit /b 1
