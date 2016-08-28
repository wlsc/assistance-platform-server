The Client Feedback Format is a channel server -> client (one way feedback). The main purpose of the channel is to provide information about module activities (cards) on the server for clients.

#### Short overview of JSON format without content
```javascript
[ {"moduleId" : string,
  "content" : { // your content type object goes here
  		},
  "created": "2015-08-04T22:04:50Z"    <--  in ISO 8601 format!
  },...
]
```

#### Content type: text item
```javascript
{
	"type" : "text",  
	"caption" : string, // free text
	"target" : string,  // (optional, aber nicht bei Button, hat eine der beiden folgenden gesetzt) - url - app
	"priority" : Integer,  // 1 - X (optional, wird nur benutzt wenn in group), standard = 1
	"alignment" : string,  // - "left", - "center", - "right"
	"style" : [], // possible features (highly discussable): 
	//- title1 (The font used for first level hierarchical headings.)
	//- title2 (The font used for second level hierarchical headings.)
	//- title3 (The font used for third level hierarchical headings.)
	//- headline (The font used for headings.)
	//- subheadline (The font used for subheadings.)
	//- body (The font used for body text.)
	//- footnote (The font used in footnotes.)
	//- caption[1] (The font used for standard captions.)
	//- caption2 (The font used for alternate captions.)
	//- callout (The font used for callouts.)
	"highlighted" : boolean
}
```

#### Content type: image item
```javascript
{
	"type" : "image",  
	"source" : string,  // URL 
	"target" : string,  // (optional, aber nicht bei Button, hat eine der beiden folgenden gesetzt) - url - app
	"priority" : Integer,  // 1 - X (optional, wird nur benutzt wenn in group), standard = 1
}
```

#### Content type: map item
```javascript
{
	"type" : "map",  
	"points" : [[2.5, 5.6],[4.2, 6.2]],
	"showUserLocation" : boolean,   // (boolean, optional, true zeigt aktuelle user location)
	"target" : string,  // (optional, aber nicht bei Button, hat eine der beiden folgenden gesetzt) - url - app
	"priority" : Integer,  // 1 - X (optional, wird nur benutzt wenn in group), standard = 1
}
```

#### Content type: button item
```javascript
{
	"type" : "button",  
	"caption" : string, // free text
	"target" : string,  // (optional, aber nicht bei Button, hat eine der beiden folgenden gesetzt) - url - app
	"priority" : Integer,  // 1 - X (optional, wird nur benutzt wenn in group), standard = 1
}
```

#### Content type: group of items
```javascript
{
	"type" : "group",  
	"alignment" : string,  // - horizontal, - vertical (optional, standard: horizontal)
	"content" : [...],   // here more content type objects
}
```