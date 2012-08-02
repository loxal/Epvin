#import('dart:html');

Map<String, String> getUriParams(String uriSearch) {
  if (uriSearch != '') {
    final List<String> paramValuePairs = uriSearch.substring(1).split('&');

    var paramMapping = new HashMap<String, String>();
    paramValuePairs.forEach((e) {
      if (e.contains('=')) {
        final paramValue = e.split('=');
        paramMapping[paramValue[0]] = paramValue[1];
      } else {
        paramMapping[e] = '';
      }
    });
    return paramMapping;
  }
}

void init() {
  final ScriptElement moduleContainer = query('#module');

  final uriSearch = window.location.search;
  final paramMapping = getUriParams(uriSearch);

  final defaultModuleFqn = 'loxal.epvin.manager.Manager';
  final moduleFqn = paramMapping['module'] == null ? defaultModuleFqn : paramMapping['module'];
  final moduleSrc = '/$moduleFqn/$moduleFqn.nocache.js';

  moduleContainer.src = moduleSrc;
  print('moduleContainer.src: ${moduleContainer.src}');
}

void main() {
  init();
}
