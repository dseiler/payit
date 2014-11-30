; example1.nsi
;
; This script is perhaps one of the simplest NSIs you can make. All of the
; optional settings are left to their default settings. The installer simply 
; prompts the user asking them where to install, and drops a copy of makensisw.exe
; there. 

;--------------------------------

; The name of the installer
Name "PayIT"

; The file to write
OutFile "payit.exe"

; The default installation directory
InstallDir $PROGRAMFILES\PayIT

BrandingText "PayIT Version 1.5"

DirText "This installer will create some shortcuts to MakeNSIS in the start menu.$\nFor this it needs NSIS's path." \
  "Please specify the path in which you have installed NSIS:"

;BGGradient

;--------------------------------

; Pages
Page custom StartMenuGroupSelect "" ": Start Menu Folder"
Page directory
Page instfiles

;--------------------------------
; functions
/*
Function .onVerifyInstDir
	IfFileExists $INSTDIR\makensis.exe +2
		Abort
FunctionEnd
*/

Function StartMenuGroupSelect
	Push $R1

	StartMenu::Select /checknoshortcuts "Don't create a start menu folder" /autoadd /lastused $R0 "PayIT"
	Pop $R1

	StrCmp $R1 "success" success
	StrCmp $R1 "cancel" done
		; error
		MessageBox MB_OK $R1
		Return
	success:
	Pop $R0

	done:
	Pop $R1
FunctionEnd

;--------------------------------
Section
       ; Set output path to the installation directory.
       SetOutPath $INSTDIR

	# this part is only necessary if you used /checknoshortcuts
	StrCpy $R1 $R0 1
	StrCmp $R1 ">" skip

		;CreateDirectory $SMPROGRAMS\$R0
		;CreateShortCut "$SMPROGRAMS\$R0\PayIT.lnk" "$INSTDIR\payit.bat" parameters "$INSTDIR\images\payit.ico" "" SW_SHOWMINIMIZED

		SetShellVarContext All
		CreateDirectory $SMPROGRAMS\$R0
		CreateShortCut "$SMPROGRAMS\$R0\PayIT.lnk" "$INSTDIR\payit.bat" parameters "$INSTDIR\images\payit.ico" "" SW_SHOWMINIMIZED

	skip:
SectionEnd

; The stuff to install
Section "" ;No components page, name is not important

  ; Put file there
  File /r build\payit-1.5\conf
  File /r build\payit-1.5\images
  File /r build\payit-1.5\lib
  File /r build\payit-1.5\res
  File /r build\payit-1.5\payit.jar
  File /r build\payit-1.5\payit.bat
  
SectionEnd ; end the section
