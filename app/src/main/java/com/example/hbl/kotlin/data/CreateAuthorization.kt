package com.example.hbl.kotlin.data

class CreateAuthorization {
    /**
     * A list of scopes that this authorization is in.
     */
    var scopes: Array<String> = arrayOf()

    /**
     * Required. A note to remind you what the OAuth token is for. Tokens not associated with a
     * specific OAuth application (i.e. personal access tokens) must have a unique note.
     */
    var note: String=""

    /**
     * A URL to remind you what app the OAuth token is for.
     */
    var note_url: String=""

    /**
     * The 20 character OAuth app client key for which to create the token.
     */
    var client_id: String=""

    /**
     * The 40 character OAuth app client secret for which to create the token.
     */
    var client_secret: String=""

    /**
     * A unique string to distinguish an authorization from others created for the same client ID and
     * user.
     */
    var fingerprint: String=""
}
