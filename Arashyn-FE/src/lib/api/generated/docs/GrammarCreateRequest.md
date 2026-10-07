# GrammarCreateRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**title** | **string** |  | [default to undefined]
**language** | **string** |  | [default to undefined]
**isPublic** | **boolean** |  | [default to undefined]
**groups** | [**Array&lt;Group&gt;**](Group.md) |  | [default to undefined]
**notes** | [**Array&lt;NoteCreateRequest&gt;**](NoteCreateRequest.md) |  | [optional] [default to undefined]
**filterIds** | **Array&lt;string&gt;** |  | [optional] [default to undefined]

## Example

```typescript
import { GrammarCreateRequest } from 'arashyn-api';

const instance: GrammarCreateRequest = {
    title,
    language,
    isPublic,
    groups,
    notes,
    filterIds,
};
```

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)
