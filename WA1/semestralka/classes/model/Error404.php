<?php

namespace model;

/**
 * Error404 situation when the file has not been found.
 *
 * @author Michal Stanke <michal.stanke@mikk.cz>
 */
class Error404 extends ModelClass {

    protected function load() {
        http_response_code(404);
    }

}
