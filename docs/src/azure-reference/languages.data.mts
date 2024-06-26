import { defineLoader } from 'vitepress'
import data from './azure_available_languages.json'

export type Languages = {
  readonly translation:     { [key: string]: Dictionary };
  readonly transliteration: { [key: string]: Transliteration };
  readonly dictionary:      { [key: string]: Dictionary };
}

export type Dictionary = {
  readonly name:          string;
  readonly nativeName:    string;
  readonly dir:           Dir;
  readonly translations?: Dictionary[];
  readonly code?:         string;
  readonly toScripts?:    Dictionary[];
}

export type Dir = "ltr" | "rtl";

export type Transliteration = {
  readonly name:       string;
  readonly nativeName: string;
  readonly scripts:    Dictionary[];
}

export default defineLoader({
  load() {
    return {
      azure_response: data as Languages
    }
  }
})
