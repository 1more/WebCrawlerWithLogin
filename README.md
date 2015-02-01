# WebCrawlerWithLogin
Web crawler which can handle login process


## Install

Clone this repository:

``` sh
git clone https://github.com/1more/WebCrawlerWithLogin.git
```

## Usage

1. Find out login process from the website which you want to login.
2. Make `URLWithOption` instance with that information.
3. Make `LoginCrawler` instance. Initialize it with `URLWithOption` instances.
4. You need to call `allowAllCertificates` method if you need to allow untrusted certificate.
5. Call `doLogin` methods for login. It makes cookie which contains login session value.
6. Call `crawl` method with url of the page you need to crawl.

## Notice

You should manage your cookies.
Session expiration does not managed automatically.