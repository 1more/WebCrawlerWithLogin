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
3. Make `LoginCrawler` instance and call `doLogin` methods with `URLWithOption` instance.
4. You need to call `allowAllCertificates` method for allow untrusted certificate.
5. Call `crawl` method with url of the page you need to crawl.

## Notice

You should manage your cookies.
Session expiration does not managed automatically.