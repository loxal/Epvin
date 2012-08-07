#import('dart:html');

Map<String, String> getUriParams(String uriSearch) {
  if (uriSearch != '') {
    final List<String> paramValuePairs = uriSearch.substring(1).split('&');
    final paramMapping= new HashMap<String, String>();

    paramValuePairs.forEach((e) {
      if (e.contains('=')) {
        final paramValue = e.split('=');
        paramMapping[paramValue[0]] = paramValue[1];
      } else {
        paramMapping[e] = '';
      }
    });

    return paramMapping;
  } else {
    return null;
  }
}

BaseElement base;
ScriptElement moduleContainer;

void init() {
  moduleContainer = new ScriptElement();

  final uriSearch = window.location.search;
  final paramMapping = getUriParams(uriSearch);

  final defaultModuleFqn = 'loxal.epvin.manager.Manager';
  final moduleFqn = paramMapping == null ? defaultModuleFqn : paramMapping['module'];
  final moduleSrc = '/$moduleFqn/$moduleFqn.nocache.js';

  moduleContainer.async = true;
  moduleContainer.defer = true;
  moduleContainer.type = 'text/javascript';
  moduleContainer.src = moduleSrc;
  document.head.elements.add(moduleContainer);

  print('gwtModule: ${moduleContainer.src}');
}

void main() {
  init();
}
