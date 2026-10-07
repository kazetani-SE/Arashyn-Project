# UserDeckCloneRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**deckId** | **string** |  | [default to undefined]
**name** | **string** |  | [optional] [default to undefined]
**userFolderId** | **string** |  | [optional] [default to undefined]
**sourceUserDeckId** | **string** |  | [optional] [default to undefined]
**userGrammars** | [**UserGrammarCreateMultipleRequest**](UserGrammarCreateMultipleRequest.md) |  | [optional] [default to undefined]

## Example

```typescript
import { UserDeckCloneRequest } from 'arashyn-api';

const instance: UserDeckCloneRequest = {
    deckId,
    name,
    userFolderId,
    sourceUserDeckId,
    userGrammars,
};
```

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)
