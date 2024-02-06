import MarkdownIt from 'markdown-it';

export const replaceTextPlugin = (md: MarkdownIt, searchText: string, replaceText: string) => {
  md.core.ruler.before('inline', 'replace-text-plugin', (state) => {

    state.tokens.forEach((token) => {
        token.content = token.content.replace(searchText, replaceText);
    });
  });
}
