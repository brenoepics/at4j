import axios from 'axios'
import { Release } from './github/Release'

export let AT4JRelease: Release | null;

export const REPO_OWNER: string = 'brenoepics'
export const REPO_NAME: string = 'at4j'
export const AT4J_VERSION = async () => {
  let AT4JRelease
  if (AT4JRelease == undefined) {
    AT4JRelease = await getLatestVersion()
  }

  return AT4JRelease != null ? AT4JRelease.tag_name : "UNKNOWN"
}

const getLatestVersion = async (): Promise<Release | null> => {
  const response = await axios.get<Release>(
    `https://api.github.com/repos/${REPO_OWNER}/${REPO_NAME}/releases/latest`
  )

  if (response.status === 200) {
    return response.data
  }

  return null;
}


