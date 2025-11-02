# Script para corregir todos los paquetes a utt.equipo.hackathon

$basePath = "c:\Users\Victo\Desktop\Hackathon\composeApp\src"

# Función para actualizar un archivo
function Update-Package {
    param([string]$filePath)
    
    $content = Get-Content $filePath -Raw -Encoding UTF8
    
    # Actualizar package declarations
    $content = $content -replace '^package util', 'package utt.equipo.hackathon.util'
    $content = $content -replace '^package data\.', 'package utt.equipo.hackathon.data.'
    $content = $content -replace '^package domain\.', 'package utt.equipo.hackathon.domain.'
    $content = $content -replace '^package di', 'package utt.equipo.hackathon.di'
    $content = $content -replace '^package presentation\.', 'package utt.equipo.hackathon.presentation.'
    $content = $content -replace '^package service', 'package utt.equipo.hackathon.service'
    
    # Actualizar imports
    $content = $content -replace 'import util\.', 'import utt.equipo.hackathon.util.'
    $content = $content -replace 'import data\.', 'import utt.equipo.hackathon.data.'
    $content = $content -replace 'import domain\.', 'import utt.equipo.hackathon.domain.'
    $content = $content -replace 'import di\.', 'import utt.equipo.hackathon.di.'
    $content = $content -replace 'import presentation\.', 'import utt.equipo.hackathon.presentation.'
    $content = $content -replace 'import service\.', 'import utt.equipo.hackathon.service.'
    
    Set-Content $filePath -Value $content -Encoding UTF8 -NoNewline
    Write-Host "Actualizado: $filePath"
}

# Procesar todos los archivos .kt
Get-ChildItem -Path $basePath -Recurse -Filter *.kt | ForEach-Object {
    Update-Package $_.FullName
}

Write-Host "Todos los archivos han sido actualizados!"
